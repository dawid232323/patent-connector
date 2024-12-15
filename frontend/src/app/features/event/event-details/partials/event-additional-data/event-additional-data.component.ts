import {Component, Input} from '@angular/core';
import {Event} from "app/shared/types/event.types";

@Component({
  selector: 'app-event-additional-data',
  templateUrl: './event-additional-data.component.html',
  styleUrl: './event-additional-data.component.scss'
})
export class EventAdditionalDataComponent {
	@Input() event!: Event;
}
