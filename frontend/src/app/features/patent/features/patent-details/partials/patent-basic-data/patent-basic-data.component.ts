import {Component, Input} from '@angular/core';
import {Patent} from "app/shared/types/patent.types";
import {getDetail} from "app/features/patent/features/patent-details/utils/patent.fn";

@Component({
  selector: 'app-patent-basic-data',
  templateUrl: './patent-basic-data.component.html',
  styleUrl: './patent-basic-data.component.scss'
})
export class PatentBasicDataComponent {
	@Input() patent!: Patent;

	readonly getDetail = getDetail;
}
