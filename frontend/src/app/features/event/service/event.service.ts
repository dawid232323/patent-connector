import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {Event, PersistEvent} from "app/shared/types/event.types";
import {Observable} from "rxjs";
import {AppEndpoints} from "app/shared/types/api.types";

@Injectable()
export class EventService {

	constructor(private apiService: ApiService) {
	}

	getEvent(eventId: number): Observable<Event> {
		return this.apiService.get<Event>(AppEndpoints.EventEndpoints.eventsBase.concat(`/${eventId}`));
	}

	createEvent(eventData: PersistEvent): Observable<Event> {
		return this.apiService.post<PersistEvent, Event>(eventData, AppEndpoints.EventEndpoints.eventsBase);
	}

	deleteEvent(eventId: number): Observable<any> {
		return this.apiService.delete(AppEndpoints.EventEndpoints.eventsBase.concat(`/${eventId}`));
	}
}
