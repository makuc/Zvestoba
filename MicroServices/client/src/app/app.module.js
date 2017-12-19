"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var http_1 = require("@angular/common/http");
var forms_1 = require("@angular/forms");
var app_routing_module_1 = require("./app-routing.module");
var app_component_1 = require("./app.component");
var uporabniki_component_1 = require("./uporabnik/uporabniki.component");
var uporabniki_dodaj_component_1 = require("./uporabnik/uporabniki-dodaj.component");
var uporabnik_podrobnosti_component_1 = require("./uporabnik/uporabnik-podrobnosti.component");
var uporabnik_service_1 = require("./uporabnik/services/uporabnik.service");
var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        core_1.NgModule({
            imports: [
                platform_browser_1.BrowserModule,
                http_1.HttpClientModule,
                app_routing_module_1.AppRoutingModule,
                forms_1.FormsModule
            ],
            declarations: [
                app_component_1.AppComponent,
                uporabniki_component_1.UporabnikiComponent,
                uporabnik_podrobnosti_component_1.UporabnikPodrobnostiComponent,
                uporabniki_dodaj_component_1.UporabnikiDodajComponent
            ],
            providers: [uporabnik_service_1.UporabnikService],
            bootstrap: [app_component_1.AppComponent]
        })
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map