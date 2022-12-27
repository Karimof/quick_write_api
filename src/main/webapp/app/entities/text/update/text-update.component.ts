import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TextFormService, TextFormGroup } from './text-form.service';
import { IText } from '../text.model';
import { TextService } from '../service/text.service';

@Component({
  selector: 'jhi-text-update',
  templateUrl: './text-update.component.html',
})
export class TextUpdateComponent implements OnInit {
  isSaving = false;
  text: IText | null = null;

  editForm: TextFormGroup = this.textFormService.createTextFormGroup();

  constructor(protected textService: TextService, protected textFormService: TextFormService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ text }) => {
      this.text = text;
      if (text) {
        this.updateForm(text);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const text = this.textFormService.getText(this.editForm);
    if (text.id !== null) {
      this.subscribeToSaveResponse(this.textService.update(text));
    } else {
      this.subscribeToSaveResponse(this.textService.create(text));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IText>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(text: IText): void {
    this.text = text;
    this.textFormService.resetForm(this.editForm, text);
  }
}
