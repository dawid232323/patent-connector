import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpParamsOptions, HttpResponse} from "@angular/common/http";
import {environment} from "src/environments/environment";
import {Observable} from "rxjs";
import {ApiOptions} from "app/shared/types/api.types";

@Injectable({
	providedIn: 'root'
})
export class ApiService {

	constructor(private httpClient: HttpClient) {
	}

	get<K>(resourcePath: string, options?: any | ApiOptions): Observable<any> {
		const url = this.getFinalUrl(resourcePath);
		return <Observable<HttpResponse<K>>>this.httpClient.get<K>(url, options);
	}

	post<T, K>(body: T, resourcePath: string, options?: any | ApiOptions): Observable<any> {
		const url = this.getFinalUrl(resourcePath);
		return <Observable<HttpResponse<K>>>this.httpClient.post<K>(url, body, options);
	}

	put<T, K=T>(resourcePath: string, body: T, options?: any | ApiOptions): Observable<any> {
		const url = this.getFinalUrl(resourcePath);
		return <Observable<K>>this.httpClient.put<K>(url, body, options);
	}

	delete(resourcePath: string): Observable<any> {
		return this.httpClient.delete(resourcePath);
	}

	private getFinalUrl(resourcePath: string): string {
		return resourcePath;
	}
}
