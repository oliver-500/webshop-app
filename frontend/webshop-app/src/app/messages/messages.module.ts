import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MessagesRoutingModule } from './messages-routing.module';
import { MessageService } from './services/message.service';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    MessagesRoutingModule
  ],
  providers: [MessageService]
})
export class MessagesModule { }
