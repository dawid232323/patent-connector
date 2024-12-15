import {Injectable} from '@angular/core';
import {ApiService} from "app/shared/service/api.service";
import {Event, PersistEvent} from "app/shared/types/event.types";
import {Observable} from "rxjs";
import {AppEndpoints} from "app/shared/types/api.types";

@Injectable()
export class EventService {

	constructor(private apiService: ApiService) {
	}

	createEvent(eventData: PersistEvent): Observable<Event> {
		return this.apiService.post<PersistEvent, Event>(eventData, AppEndpoints.EventEndpoints.eventsBase);
	}
}
