import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {RichTextEditorToolbarOptions, ValidatedForm} from "app/shared/types/util.types";
import {ValidationService} from "app/shared/utils/validation/validation.service";
import {ErrorCode} from "app/shared/utils/validation/error.types";
import {InventionDemand} from "app/shared/types/invention-demand.types";
import {ActivatedRoute} from "@angular/router";
import {BusinessBranch} from "app/shared/types/business-branch.types";
import {User, UserResearchInstitutionGroup} from "app/shared/types/user.types";
import {Editor} from "ngx-editor";
import {BehaviorSubject, debounceTime, distinctUntilChanged, Observable, Subscription} from "rxjs";
import {groupBy, isNil} from "lodash";
import {valuePresentValidator} from "app/shared/utils/validation/validators.fn";

@Component({
	selector: 'app-invention-demand-form',
	templateUrl: './invention-demand-form.component.html',
	styleUrl: './invention-demand-form.component.scss'
})
export class InventionDemandFormComponent implements OnInit, OnDestroy, ValidatedForm {

	@Output() formSubmit = new EventEmitter<InventionDemand>();

	demandForm!: FormGroup;
	contentEditor!: Editor;
	isFormSubmitted = false;
	selectedBranches: BusinessBranch[] = [];

	readonly errorCode: typeof ErrorCode = ErrorCode;
	readonly toolbarOptions = RichTextEditorToolbarOptions;

	private _businessBranches: BusinessBranch[] = [];
	private _loggedUser!: User;
	private _researchInstitutionWorkers: User[] = [];
	private _filteredBranches = new BehaviorSubject<BusinessBranch[]>([]);
	private _filteredUsers = new BehaviorSubject<UserResearchInstitutionGroup[]>([]);
	private _subscription = new Subscription();

	constructor(private formBuilder: FormBuilder,
				private validationService: ValidationService,
				private activatedRoute: ActivatedRoute) {
	}

	ngOnInit(): void {
		this.initFormUsedData();
		this.initForm();
		this.makeValueChangeSub();
	}

	ngOnDestroy() {
		this._subscription.unsubscribe();
		this.contentEditor.destroy();
	}

	getControlErrorType(control: AbstractControl): string | null {
		return this.validationService.getControlErrorType(control as FormControl);
	}

	validateAndSubmit(): void {
		this.recipientIdCtrl.markAsTouched();
		this.isFormSubmitted = true;
		if (this.demandForm.invalid) {
			return;
		}
		const formValue = this.transformFormValue();
		this.formSubmit.emit(formValue);
	}

	getBranchDisplayName(branch: BusinessBranch): string {
		return `${branch.displayName} (${branch.code})`;
	}

	addBusinessBranchOption(branch: BusinessBranch) {
		this.selectedBranches.push(branch);
		this.businessBranchInputCtrl.setValue(null);
	}

	removeBranch(branchToRemove: BusinessBranch) {
		this.selectedBranches = this.selectedBranches.filter(branch => branch.id !== branchToRemove.id);
	}

	shouldDisplayBranchesError(): boolean {
		return this.selectedBranches.length === 0 && this.isFormSubmitted;
	}

	selectUser(selectedUser: User) {
		this.recipientIdCtrl.setValue(selectedUser.id);
	}

	deleteUser() {
		this.recipientIdCtrl.setValue(null);
		this.recipientInputCtrl.setValue(null);
	}

	getUserDisplayName(user: User): string {
		return `${user.firstName} ${user.lastName} (${user.email})`;
	}

	private initFormUsedData() {
		const routeData = this.activatedRoute.snapshot.data;
		this._businessBranches = routeData['businessBranches'];
		this._loggedUser = routeData['loggedUser'];
		this._researchInstitutionWorkers = routeData['institutionWorkers'];
	}

	private initForm() {
		const usersIds: number[] = this._researchInstitutionWorkers.map(r => r.id);
		this.contentEditor = new Editor();
		this.demandForm = this.formBuilder.group({
			issuerId: [this.loggedUser.id, [Validators.required]],
			recipientId: [null, [valuePresentValidator(new Set<number>(usersIds))]],
			content: [null, [Validators.required]],
			businessBranchInput: [null],
			recipientInput: [null, [Validators.required]],
		});
	}

	private makeValueChangeSub() {
		this._subscription.add(this.businessBranchInputCtrl.valueChanges
			.pipe(distinctUntilChanged(), debounceTime(50))
			.subscribe(this.handleBusinessBranchInputValueChange.bind(this)));
		this._subscription.add(this.recipientInputCtrl.valueChanges
			.pipe(distinctUntilChanged(), debounceTime(50))
			.subscribe(this.handleUsersValueChange.bind(this)));
	}

	private handleBusinessBranchInputValueChange(typedValue: string) {
		if (isNil(typedValue) || typedValue === '') {
			this._filteredBranches.next([]);
			return;
		}
		const filtered = this._businessBranches.filter(branch =>
			this.getBranchDisplayName(branch).includes(typedValue));
		this._filteredBranches.next(filtered);
	}

	private handleUsersValueChange(typedValue: string) {
		if (isNil(typedValue) || typedValue === '') {
			this._filteredUsers.next([]);
			this.deleteUser();
			return;
		}
		const filteredUsers = this.getFilteredInstitutionWorkers(typedValue);
		const groupedWorkers = groupBy(filteredUsers, 'researchInstitution.name');
		const workers = <UserResearchInstitutionGroup[]>Object.keys(groupedWorkers).map(institutionName => {
			return {institution: institutionName, users: groupedWorkers[institutionName] as unknown as User[]};
		});
		this._filteredUsers.next(workers);
	}

	private getFilteredInstitutionWorkers(inputValue: string): User[] {
		return this._researchInstitutionWorkers.filter(worker => {
			return worker.firstName.toLowerCase().includes(inputValue.toLowerCase())
			|| worker.lastName.toLowerCase().includes(inputValue.toLowerCase())
			|| worker.email.toLowerCase().includes(inputValue.toLowerCase())
			|| worker.researchInstitution!.name.toLowerCase().includes(inputValue.toLowerCase())
		});
	}

	private transformFormValue(): InventionDemand {
		const formValue = this.demandForm.value;
		const branchesIds = this.selectedBranches.map(r => r.id);
		const issuerId = this.loggedUser.id;
		return {
			issuerId: issuerId,
			recipientId: this.recipientIdCtrl.value,
			content: formValue['content'],
			businessBranchesIds: branchesIds
		}
	}

	get loggedUser(): User {
		return this._loggedUser;
	}

	get businessBranchInputCtrl(): FormControl {
		return this.demandForm.get('businessBranchInput') as FormControl;
	}

	get recipientInputCtrl(): FormControl {
		return this.demandForm.get('recipientInput') as FormControl;
	}

	get recipientIdCtrl(): FormControl {
		return this.demandForm.get('recipientId') as FormControl;
	}

	get filteredBranches$(): Observable<BusinessBranch[]> {
		return this._filteredBranches.asObservable();
	}

	get filteredUsers$(): Observable<UserResearchInstitutionGroup[]> {
		return this._filteredUsers.asObservable();
	}

	get issuerDisplayName(): string {
		return `${this.loggedUser.firstName} ${this._loggedUser.lastName} (${this.loggedUser.email})`;
	}
}
