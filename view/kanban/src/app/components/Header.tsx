import { Scale, Plus, Search } from "lucide-react";
import { Link } from "react-router-dom";
import { useState } from "react";
import { Button } from "./ui/button";

interface HeaderProps {
	title: string;
	subtitle: string;
	showActions?: boolean;
}

export default function Header({ title, subtitle, showActions = true }: HeaderProps) {

	return (
		<header className="bg-white border-b border-gray-200 px-6 py-4">
			<div className="flex items-center justify-between gap-4">
				<div className="flex items-center gap-3">
					<div className="bg-blue-600 p-2 rounded-lg">
						<Scale className="w-6 h-6 text-white" />
					</div>
					<div>
						<h1 className="text-2xl font-bold text-gray-900">
							{title}
						</h1>
						<p className="text-sm text-gray-500">
							{subtitle}
						</p>
					</div>
				</div>

				{showActions && (
					<Button
						className="bg-blue-600 hover:bg-blue-700"
						asChild
					>
						<Link to="/novo-processo">
							<Plus className="w-4 h-4" />
							<span>Novo Processo</span>
						</Link>
					</Button>
				)}
			</div>
		</header>
	);
}
