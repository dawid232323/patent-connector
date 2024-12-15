import {Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Event, PersistEvent} from "app/shared/types/event.types";
import {isNil} from "lodash";
import {BehaviorSubject, debounceTime, distinctUntilChanged, Observable, Subscription, take} from "rxjs";
import {Editor, toHTML, toDoc} from "ngx-editor";
import {RichTextEditorToolbarOptions, ValidatedForm} from "app/shared/types/util.types";
import {ValidationService} from "app/shared/utils/validation/validation.service";
import {ErrorCode} from "app/shared/utils/validation/error.types";
import {futureDateValidator} from "app/shared/utils/validation/validators.fn";
import {ActivatedRoute, Router} from "@angular/router";
import {BusinessBranch} from "app/shared/types/business-branch.types";

@Component({
	selector: 'app-event-form',
	templateUrl: './event-form.component.html',
	styleUrl: './event-form.component.scss'
})
export class EventFormComponent implements OnInit, OnDestroy, ValidatedForm {

	@Input() editedEvent?: Event;

	@Output() eventSubmit = new EventEmitter<PersistEvent>();

	eventForm!: FormGroup;
	descriptionEditor!: Editor;
	registrationDetailsEditor!: Editor;
	isFormSubmitted = false;
	selectedBranches: BusinessBranch[] = [];

	readonly errorCode = ErrorCode;
	readonly toolbarOptions = RichTextEditorToolbarOptions;

	private _subscription = new Subscription();
	private _baseBusinessBranches: BusinessBranch[] = [];
	private _filteredBranches = new BehaviorSubject<BusinessBranch[]>([]);

	constructor(private formBuilder: FormBuilder,
				private route: ActivatedRoute,
				private router: Router,
				public validationService: ValidationService) {
	}

	ngOnInit() {
		this.initBusinessBranches();
		this.initEditors();
		this.initForm();
		this.makeValueChangesSub();
		this.setFormInitialValues();
	}

	ngOnDestroy() {
		this._subscription.unsubscribe();
		this.descriptionEditor.destroy();
		this.registrationDetailsEditor.destroy();
	}

	getControlErrorType(control: AbstractControl): string | null {
		return this.validationService.getControlErrorType(<FormControl>control);
	}

	validateAndSubmit() {
		this.isFormSubmitted = true;
		this.eventForm.markAllAsTouched();
		if (this.eventForm.invalid || this.shouldDisplayBranchesError()) {
			return;
		}
		this.emitEventData();
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

	cancel() {
		if (isNil(this.editedEvent)) {
			return this.router.navigate(['/events', 'listing']);
		}
		return this.router.navigate(['/events', this.editedEvent!.id]);
	}

	private initBusinessBranches() {
		this._baseBusinessBranches = this.route.snapshot.data['businessBranches'];
	}

	private initEditors() {
		this.descriptionEditor = new Editor();
		this.registrationDetailsEditor = new Editor();
	}

	private initForm() {
		this.eventForm = this.formBuilder.group({
			title: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(200)]],
			startDate: [null, [Validators.required, futureDateValidator()]],
			singleDayEvent: [true, [Validators.required]],
			endDate: [{value: null, disabled: true}, [futureDateValidator()]],
			localization: [null, Validators.maxLength(300)],
			description: [null, [Validators.required]],
			contactEmail: [null, [Validators.email, Validators.maxLength(70)]],
			contactPhone: [null, [Validators.maxLength(9)]],
			registrationDetails: [null, [Validators.required]],
			businessBranchInput: [null]
		});
	}

	private makeValueChangesSub() {
		this._subscription.add(this.singleDayControl.valueChanges
			.subscribe(this.handleSingleDayEventChange.bind(this)));
		this._subscription.add(this.businessBranchInputCtrl.valueChanges
			.pipe(distinctUntilChanged(), debounceTime(50))
			.subscribe(this.handleBusinessBranchInputValueChange.bind(this)));
	}

	private setFormInitialValues() {
		if (isNil(this.editedEvent)) {
			return;
		}
		const isSingleDay = this.editedEvent.startDate === this.editedEvent.endDate || isNil(this.editedEvent.endDate);
		this.selectedBranches = [...this.editedEvent.businessBranches];
		this.eventForm.patchValue({
			title: this.editedEvent.title,
			startDate: this.editedEvent.startDate,
			singleDayEvent: isSingleDay,
			endDate: isSingleDay ? null : this.editedEvent.endDate,
			localization: this.editedEvent.localization,
			contactEmail: this.editedEvent.contactEmail,
			contactPhone: this.editedEvent.contactPhone,
			description: toDoc(this.editedEvent.description),
			registrationDetails: toDoc(this.editedEvent.registrationDetails)
		});
	}

	private handleSingleDayEventChange(isSingleDay: boolean) {
		if (isSingleDay) {
			this.endDateControl.setValue(null, {emitEvent: false});
			this.endDateControl.removeValidators(Validators.required);
			this.endDateControl.disable({emitEvent: false});
		} else {
			this.endDateControl.addValidators([Validators.required]);
			this.endDateControl.enable({emitEvent: false});
		}
		this.endDateControl.updateValueAndValidity();
	}

	private handleBusinessBranchInputValueChange(typedValue: string) {
		if (isNil(typedValue) || typedValue === '') {
			this._filteredBranches.next([]);
			return;
		}
		const filtered = this._baseBusinessBranches.filter(branch =>
			this.getBranchDisplayName(branch).includes(typedValue));
		this._filteredBranches.next(filtered);
	}

	private emitEventData() {
		const formValue = this.eventForm.value;
		const endDate: Date = formValue['singleDayEvent'] === true ? formValue['startDate'] : formValue['endDate'];
		const businessBranchesIds: number[] = this.selectedBranches.map(branch => branch.id);
		const eventData: PersistEvent = {
			title: formValue['title'],
			startDate: formValue['startDate'],
			endDate: endDate,
			localization: formValue['localization'],
			description: toHTML(formValue['description']),
			contactEmail: formValue['contactEmail'],
			contactPhone: formValue['contactPhone'],
			registrationDetails: toHTML(formValue['registrationDetails']),
			businessBranchesIds
		};
		this.eventSubmit.emit(eventData);
	}

	get titleControl(): FormControl {
		return this.eventForm.get('title') as FormControl;
	}

	get startDateControl(): FormControl {
		return this.eventForm.get('startDate') as FormControl;
	}

	get singleDayControl(): FormControl {
		return this.eventForm.get('singleDayEvent') as FormControl;
	}

	get endDateControl(): FormControl {
		return this.eventForm.get('endDate') as FormControl;
	}

	get localizationControl(): FormControl {
		return this.eventForm.get('localization') as FormControl;
	}

	get contactEmailControl(): FormControl {
		return this.eventForm.get('contactEmail') as FormControl;
	}

	get contactPhoneControl(): FormControl {
		return this.eventForm.get('contactPhone') as FormControl;
	}

	get businessBranchInputCtrl(): FormControl {
		return this.eventForm.get('businessBranchInput') as FormControl;
	}

	get filteredBranches$(): Observable<BusinessBranch[]> {
		return this._filteredBranches.asObservable();
	}
}
