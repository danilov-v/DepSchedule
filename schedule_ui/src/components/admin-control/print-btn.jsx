import React from "react";
import { Button } from "reactstrap";

export function PrintBtn({ text, disabled }) {
  const print = () => window.print();

  return (
    <Button
      color="primary"
      disabled={disabled}
      className="mr-3"
      text={"text"}
      onClick={print}
    >
      Печать
    </Button>
  );
}
