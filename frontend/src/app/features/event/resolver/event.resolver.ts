import {ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot} from '@angular/router';
import {Event} from "app/shared/types/event.types";
import {inject} from "@angular/core";
import {EventService} from "app/features/event/service/event.service";

export const eventResolver: ResolveFn<Event> = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
	const eventId = +route.params['id'];
	const eventService = inject(EventService);
	return eventService.getEvent(eventId);
};
