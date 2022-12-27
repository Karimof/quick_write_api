import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TextDetailComponent } from './text-detail.component';

describe('Text Management Detail Component', () => {
  let comp: TextDetailComponent;
  let fixture: ComponentFixture<TextDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TextDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ text: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TextDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TextDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load text on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.text).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
