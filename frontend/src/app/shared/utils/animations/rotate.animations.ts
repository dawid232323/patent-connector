import {AnimationTriggerMetadata, trigger, state, style, transition, animate} from "@angular/animations";

export const halfRotateRightAnimation: AnimationTriggerMetadata = trigger('halfRotateAnimation', [
	state('default', style({transform: 'rotate(0deg)'})),
	state('rotate', style({transform: 'rotate(180deg)'})),
	transition("default <=> rotate", animate('200ms ease-in-out'))
]);
