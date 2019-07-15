import React, { Fragment, useState, useEffect } from "react";

import "./home.scss";

function useCustonHook(initState = 0) {
  const [count, setCount] = useState(initState);

  return [count, setCount];
}

export function Home() {
  const [count, setCount] = useCustonHook(0);

  useEffect(() => {
    console.log("effect");
  });

  return (
    <Fragment>
      <div className="home">Hello Dep Scheduler project - {count}</div>
      <button onClick={() => setCount(count + 1)}>Click me</button>
    </Fragment>
  );
}
