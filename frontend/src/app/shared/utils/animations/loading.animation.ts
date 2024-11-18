import {trigger, style, animate, transition, state} from '@angular/animations';

export const loadingFromSideAnimation = trigger('loadingFromSideAnimation', [
	transition(':enter', [
		style({
			width: '0%',
			opacity: 0,
			transform: 'translateX(-100%)'
		}),
		animate('500ms ease-out', style({
			width: '100%',
			opacity: 1,
			transform: 'translateX(0)'
		}))
	])
]);

export const loadingFromSideAnimationReversed = trigger('loadingFromSideAnimationReversed', [
	transition(':enter', [
		style({
			width: '0%',
			opacity: 0,
			transform: 'translateX(100%)'
		}),
		animate('500ms ease-out', style({
			width: '100%',
			opacity: 1,
			transform: 'translateX(0)'
		}))
	])
]);

export const slideInOutAnimation  = trigger('slideInOut', [
		state('in', style({ transform: 'translateX(0%)', opacity: 1 })),
		state('out', style({ transform: 'translateX(-100%)', opacity: 0 })),
		transition('in <=> out', animate('500ms ease-in-out')),
]);
