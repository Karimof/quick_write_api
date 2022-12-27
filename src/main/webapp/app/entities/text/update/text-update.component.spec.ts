import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TextFormService } from './text-form.service';
import { TextService } from '../service/text.service';
import { IText } from '../text.model';

import { TextUpdateComponent } from './text-update.component';

describe('Text Management Update Component', () => {
  let comp: TextUpdateComponent;
  let fixture: ComponentFixture<TextUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let textFormService: TextFormService;
  let textService: TextService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TextUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TextUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TextUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    textFormService = TestBed.inject(TextFormService);
    textService = TestBed.inject(TextService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const text: IText = { id: 456 };

      activatedRoute.data = of({ text });
      comp.ngOnInit();

      expect(comp.text).toEqual(text);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IText>>();
      const text = { id: 123 };
      jest.spyOn(textFormService, 'getText').mockReturnValue(text);
      jest.spyOn(textService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ text });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: text }));
      saveSubject.complete();

      // THEN
      expect(textFormService.getText).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(textService.update).toHaveBeenCalledWith(expect.objectContaining(text));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IText>>();
      const text = { id: 123 };
      jest.spyOn(textFormService, 'getText').mockReturnValue({ id: null });
      jest.spyOn(textService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ text: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: text }));
      saveSubject.complete();

      // THEN
      expect(textFormService.getText).toHaveBeenCalled();
      expect(textService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IText>>();
      const text = { id: 123 };
      jest.spyOn(textService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ text });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(textService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
