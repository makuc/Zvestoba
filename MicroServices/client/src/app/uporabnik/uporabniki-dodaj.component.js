"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var uporabnik_1 = require("./models/uporabnik");
var uporabnik_service_1 = require("./services/uporabnik.service");
var UporabnikiDodajComponent = (function () {
    function UporabnikiDodajComponent(uporabnikService, router) {
        this.uporabnikService = uporabnikService;
        this.router = router;
        this.uporabnik = new uporabnik_1.Uporabnik;
    }
    UporabnikiDodajComponent.prototype.submitForm = function () {
        var _this = this;
        this.uporabnikService
            .create(this.uporabnik)
            .then(function () {
            _this.router.navigate(['/uporabniki']);
        });
    };
    UporabnikiDodajComponent.prototype.nazaj = function () {
        this.router.navigate(['/uporabniki']);
    };
    UporabnikiDodajComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'dodaj-uporabniki',
            templateUrl: 'uporabniki-dodaj.component.html'
        }),
        __metadata("design:paramtypes", [uporabnik_service_1.UporabnikService,
            router_1.Router])
    ], UporabnikiDodajComponent);
    return UporabnikiDodajComponent;
}());
exports.UporabnikiDodajComponent = UporabnikiDodajComponent;
//# sourceMappingURL=uporabniki-dodaj.component.js.map