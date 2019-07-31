export const getLastGenUnits = units => {
  const getUnitLastChilds = unit =>
    unit.childUnit && unit.childUnit.length
      ? unit.childUnit.map(getUnitLastChilds)
      : [unit];

  return units.map(unit => getUnitLastChilds(unit).flat(4));
};
