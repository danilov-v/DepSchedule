export const UNITS = [
  {
    title: "Система управления 1",
    unitLevel: 1,
    unitId: 1,
    childUnit: [
      {
        title: "Система управления 1.1",
        parentId: 1,
        unitLevel: 2,
        unitId: 201,
        childUnit: [
          {
            title: "Система управления 1.1.1",
            parentId: 201,
            unitLevel: 3,
            unitId: 251,
          },
        ],
      },
    ],
  },
  {
    title: "Система управления 2",
    unitLevel: 1,
    unitId: 2,
    childUnit: [
      {
        title: "Орган управления 2.1",
        parentId: 2,
        unitLevel: 2,
        unitId: 3,
        childUnit: [
          {
            title: "Пункт управления 2.1.1",
            parentId: 3,
            unitLevel: 3,
            unitId: 4,
            childUnit: [
              {
                title: "Подразделение 2.1.1.1",
                parentId: 4,
                unitLevel: 4,
                unitId: 5,
              },
              {
                title: "Подразделение 2.1.1.2",
                parentId: 4,
                unitLevel: 4,
                unitId: 51,
              },
            ],
          },
        ],
      },
      {
        title: "Орган управления 2.2",
        parentId: 2,
        unitLevel: 2,
        unitId: 33,
        childUnit: null,
      },
    ],
  },
];
