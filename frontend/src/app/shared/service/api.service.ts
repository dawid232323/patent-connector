import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpParamsOptions, HttpResponse} from "@angular/common/http";
import {environment} from "src/environments/environment";
import {Observable} from "rxjs";

@Injectable({
	providedIn: 'root'
})
export class ApiService {

	constructor(private httpClient: HttpClient) {
	}

	post<T, K>(body: T, resourcePath: string, options?: any): Observable<any> {
		const url = this.getFinalUrl(resourcePath);
		return <Observable<HttpResponse<K>>>this.httpClient.post<K>(url, body, options);
	}

	private getFinalUrl(resourcePath: string): string {
		if (resourcePath.startsWith('/')) {
			return `${environment.apiUrl}${resourcePath}`;
		}
		return `${environment.apiUrl}/${resourcePath}`;
	}
}
