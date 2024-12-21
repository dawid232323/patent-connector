import {Component, Input} from '@angular/core';
import {Event} from "app/shared/types/event.types";

@Component({
  selector: 'app-event-card',
  templateUrl: './event-card.component.html',
  styleUrl: './event-card.component.scss'
})
export class EventCardComponent {
	@Input() event!: Event;
}
