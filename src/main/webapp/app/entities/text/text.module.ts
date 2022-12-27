import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TextComponent } from './list/text.component';
import { TextDetailComponent } from './detail/text-detail.component';
import { TextUpdateComponent } from './update/text-update.component';
import { TextDeleteDialogComponent } from './delete/text-delete-dialog.component';
import { TextRoutingModule } from './route/text-routing.module';

@NgModule({
  imports: [SharedModule, TextRoutingModule],
  declarations: [TextComponent, TextDetailComponent, TextUpdateComponent, TextDeleteDialogComponent],
})
export class TextModule {}
