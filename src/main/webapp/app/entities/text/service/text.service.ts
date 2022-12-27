import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IText, NewText } from '../text.model';

export type PartialUpdateText = Partial<IText> & Pick<IText, 'id'>;

export type EntityResponseType = HttpResponse<IText>;
export type EntityArrayResponseType = HttpResponse<IText[]>;

@Injectable({ providedIn: 'root' })
export class TextService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/texts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(text: NewText): Observable<EntityResponseType> {
    return this.http.post<IText>(this.resourceUrl, text, { observe: 'response' });
  }

  update(text: IText): Observable<EntityResponseType> {
    return this.http.put<IText>(`${this.resourceUrl}/${this.getTextIdentifier(text)}`, text, { observe: 'response' });
  }

  partialUpdate(text: PartialUpdateText): Observable<EntityResponseType> {
    return this.http.patch<IText>(`${this.resourceUrl}/${this.getTextIdentifier(text)}`, text, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IText>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IText[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTextIdentifier(text: Pick<IText, 'id'>): number {
    return text.id;
  }

  compareText(o1: Pick<IText, 'id'> | null, o2: Pick<IText, 'id'> | null): boolean {
    return o1 && o2 ? this.getTextIdentifier(o1) === this.getTextIdentifier(o2) : o1 === o2;
  }

  addTextToCollectionIfMissing<Type extends Pick<IText, 'id'>>(
    textCollection: Type[],
    ...textsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const texts: Type[] = textsToCheck.filter(isPresent);
    if (texts.length > 0) {
      const textCollectionIdentifiers = textCollection.map(textItem => this.getTextIdentifier(textItem)!);
      const textsToAdd = texts.filter(textItem => {
        const textIdentifier = this.getTextIdentifier(textItem);
        if (textCollectionIdentifiers.includes(textIdentifier)) {
          return false;
        }
        textCollectionIdentifiers.push(textIdentifier);
        return true;
      });
      return [...textsToAdd, ...textCollection];
    }
    return textCollection;
  }
}
