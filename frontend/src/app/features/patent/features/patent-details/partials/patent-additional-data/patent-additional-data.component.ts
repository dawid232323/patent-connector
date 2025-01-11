import {Component, Input} from '@angular/core';
import {Patent} from "app/shared/types/patent.types";
import {
	getDetail,
	getDateDetail,
	isPatentDetailEmpty, isUsageDescEmpty
} from "app/features/patent/features/patent-details/utils/patent.fn";

@Component({
  selector: 'app-patent-additional-data',
  templateUrl: './patent-additional-data.component.html',
  styleUrl: './patent-additional-data.component.scss'
})
export class PatentAdditionalDataComponent {

	@Input() patent!: Patent;

	readonly isDetailEmpty = isPatentDetailEmpty;
	readonly getDetail = getDetail;
	readonly getDateDetail = getDateDetail;
	readonly isUsageEmpty = isUsageDescEmpty;
	readonly object = Object;
}
