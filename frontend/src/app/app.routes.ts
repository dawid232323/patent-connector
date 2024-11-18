import { Routes } from '@angular/router';
import {COMMON_ROUTES} from "app/shared/utils/routes/common.routes";
import {PATENT_ROUTES} from "app/shared/utils/routes/patent.routes";

export const routes: Routes = [
	...COMMON_ROUTES,
	...PATENT_ROUTES
];
