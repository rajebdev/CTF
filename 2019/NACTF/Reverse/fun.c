
undefined8 FUN_080491b6(char *param_1)

{
  ulonglong uVar1;
  uint uVar2;
  bool bVar3;
  char *local_10;
  uint local_c;
  int local_8;
  
  local_c = 0;
  local_8 = 0;
  local_10 = param_1;
  while (local_10 < param_1 + 8) {
    uVar1 = (ulonglong)local_c;
    local_c = (uint)(uVar1 * 0x3e);
    local_8 = local_8 * 0x3e + (int)(uVar1 * 0x3e >> 0x20);
    if (('@' < *local_10) && (*local_10 < '[')) {
      uVar2 = (int)*local_10 - 0x41;
      bVar3 = CARRY4(local_c,uVar2);
      local_c = local_c + uVar2;
      local_8 = local_8 + ((int)uVar2 >> 0x1f) + (uint)bVar3;
    }
    if (('`' < *local_10) && (*local_10 < '{')) {
      uVar2 = (int)*local_10 - 0x47;
      bVar3 = CARRY4(local_c,uVar2);
      local_c = local_c + uVar2;
      local_8 = local_8 + ((int)uVar2 >> 0x1f) + (uint)bVar3;
    }
    if (('/' < *local_10) && (*local_10 < ':')) {
      uVar2 = (int)*local_10 + 4;
      bVar3 = CARRY4(local_c,uVar2);
      local_c = local_c + uVar2;
      local_8 = local_8 + ((int)uVar2 >> 0x1f) + (uint)bVar3;
    }
    local_10 = local_10 + 1;
  }
  return CONCAT44(local_8,local_c);
}

