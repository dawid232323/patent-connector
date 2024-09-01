import {HttpInterceptorFn} from '@angular/common/http';

// TODO extend for login
export const httpInterceptor: HttpInterceptorFn = (req, next) => {
	req = req.clone({
		setHeaders: {
			'Content-Type': 'application/json',
		}
	})
	return next(req);
};
