import {ApplicationConfig, ErrorHandler, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptors, withInterceptorsFromDi} from "@angular/common/http";
import {httpInterceptor} from "app/shared/interceptor/http.interceptor";
import {globalErrorHandler} from "app/shared/handler/error.handler";
import {HttpErrorInterceptor} from "app/shared/interceptor/http-error.interceptor";

export const appConfig: ApplicationConfig = {
	providers: [
		provideZoneChangeDetection({eventCoalescing: true}),
		provideRouter(routes),
		provideAnimationsAsync(),
		provideHttpClient(
			withInterceptors([
				httpInterceptor
			]),
			withInterceptorsFromDi()
		),
		{ provide: ErrorHandler, useFactory: globalErrorHandler },
		{ provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true }
	]
};
