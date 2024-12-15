import {Component, Input} from '@angular/core';
import {Event} from "app/shared/types/event.types";
import {getDetail} from "app/features/patent/features/patent-details/utils/patent.fn";

@Component({
  selector: 'app-event-basic-data',
  templateUrl: './event-basic-data.component.html',
  styleUrl: './event-basic-data.component.scss'
})
export class EventBasicDataComponent {
	@Input() event!: Event;

	readonly getDetail = getDetail;
}
