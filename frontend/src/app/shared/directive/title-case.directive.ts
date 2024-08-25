import {Directive, ElementRef, HostListener} from '@angular/core';
import {TitleCasePipe} from "@angular/common";

@Directive({
	selector: 'input[appTitleCase]',
	standalone: true
})
export class TitleCaseDirective {

	private titleCasePipe: TitleCasePipe = new TitleCasePipe();

	constructor(private el: ElementRef) {
	}

	@HostListener('input', ['$event']) onInputChange(event: Event): void {
		const inputElement = event.target as HTMLInputElement;
		inputElement.value = this.titleCasePipe.transform(inputElement.value);
	}
}
