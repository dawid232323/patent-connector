import {Component, Input} from '@angular/core';
import {PatentAddressBook} from "app/shared/types/patent.types";
import {getDetail,} from "app/features/patent/features/patent-details/utils/patent.fn";

@Component({
  selector: 'app-patent-address-book-details',
  templateUrl: './patent-address-book-details.component.html',
  styleUrl: './patent-address-book-details.component.scss'
})
export class PatentAddressBookDetailsComponent {
	@Input() sectionTitle!: string;
	@Input() sectionSubtitle?: string;
	@Input() data: PatentAddressBook[] = [];

	readonly getDetail = getDetail;
}
