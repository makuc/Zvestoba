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
var uporabnik_service_1 = require("./services/uporabnik.service");
var UporabnikiComponent = (function () {
    function UporabnikiComponent(uporabnikService, router) {
        this.uporabnikService = uporabnikService;
        this.router = router;
    }
    UporabnikiComponent.prototype.getUporabniki = function () {
        var _this = this;
        this.uporabnikService
            .getUporabniki()
            .then(function (uporabniki) { return _this.uporabniki = uporabniki; });
    };
    UporabnikiComponent.prototype.ngOnInit = function () {
        this.getUporabniki();
    };
    UporabnikiComponent.prototype.naPodrobnosti = function (uporabnik) {
        this.uporabnik = uporabnik;
        this.router.navigate(['/uporabniki', this.uporabnik.uporabnisko_ime]);
    };
    UporabnikiComponent.prototype.delete = function (uporabnik) {
        var _this = this;
        this.uporabnikService
            .delete(uporabnik.uporabnisko_ime)
            .then(function () {
            _this.uporabniki = _this.uporabniki.filter(function (u) { return u !== uporabnik; });
            if (_this.uporabnik === uporabnik) {
                _this.uporabnik = null;
            }
        });
    };
    UporabnikiComponent.prototype.dodajUporabnika = function () {
        this.router.navigate(['/dodajuporabnika']);
    };
    UporabnikiComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'vsi-uporabniki',
            templateUrl: 'uporabniki.component.html'
        }),
        __metadata("design:paramtypes", [uporabnik_service_1.UporabnikService,
            router_1.Router])
    ], UporabnikiComponent);
    return UporabnikiComponent;
}());
exports.UporabnikiComponent = UporabnikiComponent;
//# sourceMappingURL=uporabniki.component.js.map