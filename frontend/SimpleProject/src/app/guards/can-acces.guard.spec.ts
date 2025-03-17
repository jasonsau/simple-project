import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { canAccesGuard } from './can-acces.guard';

describe('canAccesGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => canAccesGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
