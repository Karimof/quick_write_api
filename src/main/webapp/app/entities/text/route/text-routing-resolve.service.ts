import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IText } from '../text.model';
import { TextService } from '../service/text.service';

@Injectable({ providedIn: 'root' })
export class TextRoutingResolveService implements Resolve<IText | null> {
  constructor(protected service: TextService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IText | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((text: HttpResponse<IText>) => {
          if (text.body) {
            return of(text.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
