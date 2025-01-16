import {Component, Input} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatPrefix, MatSuffix} from "@angular/material/form-field";
import {halfRotateRightAnimation} from "app/shared/utils/animations/rotate.animations";
import {NgIf} from "@angular/common";
import {fastAppearAnimation} from "app/shared/utils/animations/appear.animation";

@Component({
	selector: 'app-comment',
	standalone: true,
	imports: [
		MatButton,
		MatIcon,
		MatSuffix,
		MatPrefix,
		NgIf
	],
	templateUrl: './comment.component.html',
	styleUrl: './comment.component.scss',
	animations: [halfRotateRightAnimation, fastAppearAnimation]
})
export class CommentComponent {

	@Input() isReplay: boolean = false;

	repliesShown = false;

	showRepliesSection(): boolean {
		if (this.isReplay) {
			return false;
		}
		return true;
	}
}
