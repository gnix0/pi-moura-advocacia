import { createRoot } from "react-dom/client";
import App from "./app/App.tsx";
// @ts-ignore: side-effect CSS import is handled by the bundler
import "./styles/index.css";
import React from "react";
import { BrowserRouter } from "react-router-dom";

createRoot(document.getElementById("root")!).render(
	<React.StrictMode>
		<BrowserRouter>
			<App />
		</BrowserRouter>
	</React.StrictMode>
);

