import {Component, Input} from '@angular/core';
import {PatentSearchResult} from "app/shared/types/patent.types";

@Component({
  selector: 'app-patent-card',
  templateUrl: './patent-card.component.html',
  styleUrl: './patent-card.component.scss'
})
export class PatentCardComponent {

	@Input() patent!: PatentSearchResult;
}
