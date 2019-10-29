import { isEmpty, pick, cloneDeep } from "lodash";

export const getLastGenUnits = units => {
  const getUnitLastChilds = unit =>
    unit.childUnit && unit.childUnit.length
      ? unit.childUnit.map(getUnitLastChilds)
      : [unit];

  return units
    .map(unit => getUnitLastChilds(unit))
    .reduce((lastChilds, group) => [...lastChilds, ...group], [])
    .flat(5);
};

export function getUnitsFromUnitsTree(root) {
  if (!root.length) return [];

  const stack = cloneDeep(root).reverse();
  const array = [];

  while (stack.length !== 0) {
    const node = stack.pop();
    if (!isEmpty(node.childUnit)) {
      node.childUnit.forEach(unit => {
        stack.push(unit);
      });
    }
    array.push(pick(node, ["parentId", "title", "unitId", "flag", "planned"]));
  }

  return array;
}
