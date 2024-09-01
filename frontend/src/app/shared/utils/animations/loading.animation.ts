import { trigger, style, animate, transition } from '@angular/animations';

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
