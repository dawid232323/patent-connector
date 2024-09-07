import {ApplicationConfig, ErrorHandler, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {httpInterceptor} from "app/shared/interceptor/http.interceptor";
import {globalErrorHandler} from "app/shared/handler/error.handler";

export const appConfig: ApplicationConfig = {
	providers: [
		provideZoneChangeDetection({eventCoalescing: true}),
		provideRouter(routes),
		provideAnimationsAsync(),
		provideHttpClient(withInterceptors([
			httpInterceptor
		])),
		{ provide: ErrorHandler, useFactory: globalErrorHandler }
	]
};
