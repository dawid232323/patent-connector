import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Event} from "app/shared/types/event.types";
import {UserService} from "app/shared/service/user.service";
import {map, Observable, of} from "rxjs";
import {User} from "app/shared/types/user.types";
import {isNil} from "lodash";
import {ConfirmationDialogService} from "app/shared/dialog/confirmation-dialog/service/confirmation-dialog.service";
import {ConfirmationDialogAction} from "app/shared/dialog/confirmation-dialog/types/confirmation-dialog.types";
import {EventService} from "app/features/event/service/event.service";

@Component({
	selector: 'app-event-details',
	templateUrl: './event-details.component.html',
	styleUrl: './event-details.component.scss'
})
export class EventDetailsComponent implements OnInit {

	event!: Event;

	constructor(private activatedRoute: ActivatedRoute,
				private userService: UserService,
				private confirmationService: ConfirmationDialogService,
				private eventService: EventService,
				private router: Router) {
	}

	ngOnInit() {
		this.initEventData();
	}

	deleteEvent() {
		const dialogRef = this.confirmationService.openDialog({
			title: 'Czy na pewno chcesz usunąć wydarzenie?',
			message: `Usunięcie wydarzenia "${this.event.title}" będzie nieodwracalne`
		});
		dialogRef.afterClosed().subscribe(userAction => {
			if (userAction === ConfirmationDialogAction.ACCEPT) {
				this.handleDeleteEvent();
			}
		});
	}

	private initEventData() {
		this.event = this.activatedRoute.snapshot.data['event'];
	}

	private handleDeleteEvent() {
		this.eventService.deleteEvent(this.event.id).subscribe(() => {
			this.router.navigate(['/patents', 'listing']);
		});
	}

	get canEditEvent$(): Observable<boolean> {
		if (isNil(this.event)) {
			return of(false);
		}
		return this.userService.getLoggedUserDetails()
			.pipe(map((user: User | null) => {
				if (isNil(user)) {
					return false;
				}
				return user.id === this.event.caregiver.id;
			}));
	}
}
