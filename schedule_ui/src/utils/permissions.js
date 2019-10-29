import { ROLES_PERMISSIONS } from "constants/permissions";

export const checkPermission = (role, permission) => {
  if (!role || !ROLES_PERMISSIONS[role]) {
    console.log("no such role in permissions list, chek permissions file");
    return false;
  }

  return ROLES_PERMISSIONS[role].includes(permission);
};
