import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {Event, EventSearchQuery, PersistEvent} from "app/shared/types/event.types";
import {Observable} from "rxjs";
import {AppEndpoints, PagedResult} from "app/shared/types/api.types";

@Injectable()
export class EventService {

	constructor(private apiService: ApiService) {
	}

	searchEvents(params: EventSearchQuery): Observable<PagedResult<Event>> {
		return this.apiService.get<Event>(AppEndpoints.EventEndpoints.eventsSearch, {params: params});
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

	editEvent(body: PersistEvent, eventId: number): Observable<Event> {
		return this.apiService.put<PersistEvent, Event>(AppEndpoints.EventEndpoints.eventsBase.concat(`/${eventId}`), body);
	}
}
