export const getLastGenUnits = units => {
  console.log(units);

  const getUnitLastChilds = unit =>
    unit.childUnit && unit.childUnit.length
      ? unit.childUnit.map(getUnitLastChilds)
      : [unit];

  return units.map(unit => getUnitLastChilds(unit).flat(4));
};
