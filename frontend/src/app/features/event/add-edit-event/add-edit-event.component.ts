import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormUsageMode} from "app/shared/types/util.types";
import {Event, PersistEvent} from "app/shared/types/event.types";
import {EventService} from "app/features/event/service/event.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ErrorDialogService} from "app/shared/dialog/error-dialog/service/error-dialog.service";

@Component({
	selector: 'app-add-edit-event',
	templateUrl: './add-edit-event.component.html',
	styleUrl: './add-edit-event.component.scss'
})
export class AddEditEventComponent implements OnInit, OnDestroy {

	usageMode!: FormUsageMode;
	event?: Event;


	readonly formUsageMode = FormUsageMode;

	constructor(private activatedRoute: ActivatedRoute,
				private eventService: EventService,
				private router: Router,
				private errorDialogService: ErrorDialogService) {
	}

	ngOnInit() {
		this.determineUsageModeAndSetData();
	}

	ngOnDestroy() {
	}

	handleEventDataSubmit(data: PersistEvent) {
		if (this.usageMode === FormUsageMode.EDIT) {
			this.editEvent(data);
		} else {
			this.createEvent(data);
		}
	}

	private createEvent(data: PersistEvent) {
		this.eventService.createEvent(data).subscribe({
			next: (createdEvent) => this.router.navigate(['/events', createdEvent.id]),
			error: (err) => this.handleError(err)
		});
	}

	private editEvent(data: PersistEvent) {
		this.eventService.editEvent(data, this.event!.id).subscribe({
			next: (editedEvent) => this.router.navigate(['/events', editedEvent.id]),
			error: (err) => this.handleError(err)
		});
	}

	private handleError(error: HttpErrorResponse) {
		this.errorDialogService.openDefaultErrorResponse(error);
	}

	private determineUsageModeAndSetData() {
		 this.usageMode = <FormUsageMode>this.activatedRoute.snapshot.data['mode'];
		 if (this.usageMode === FormUsageMode.EDIT) {
			 this.event = this.activatedRoute.snapshot.data['event'];
		 }
	}

	get formTitle(): string {
		if (this.usageMode === FormUsageMode.CREATE) {
			return 'Dodaj Wydarzenie';
		}
		return `Edytuj ${this.event?.title || 'Wydarzenie'}`;
	}
}
