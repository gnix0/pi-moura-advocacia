import { ProcessCard } from "../app/types/kanban";

const STORAGE_KEY = "kanban_processes_v1";

function readProcessesFromStorage(): ProcessCard[] {
	try {
		const raw = localStorage.getItem(STORAGE_KEY);
		if (!raw) return [];

		const parsed = JSON.parse(raw);
		return Array.isArray(parsed) ? (parsed as ProcessCard[]) : [];
	} catch {
		return [];
	}
}

function writeProcessesToStorage(processes: ProcessCard[]): void {
	localStorage.setItem(STORAGE_KEY, JSON.stringify(processes));
}

function generateId(): string {
	if (
		typeof crypto !== "undefined" &&
		typeof crypto.randomUUID === "function"
	) {
		return crypto.randomUUID();
	}

	return `${Date.now()}-${Math.random().toString(36).slice(2, 10)}`;
}

export async function getProcesses(): Promise<ProcessCard[]> {
	return readProcessesFromStorage();
}

export async function getProcessById(id: string): Promise<ProcessCard> {
	const process = readProcessesFromStorage().find((item) => item.id === id);
	if (!process) {
		throw new Error(`Processo com id ${id} nao encontrado`);
	}

	return process;
}

export async function createProcess(
	data: Omit<ProcessCard, "id">,
): Promise<ProcessCard> {
	const processes = readProcessesFromStorage();
	const newProcess: ProcessCard = {
		id: generateId(),
		...data,
	};

	writeProcessesToStorage([...processes, newProcess]);
	return newProcess;
}

export async function updateProcess(
	id: string,
	data: Partial<ProcessCard>,
): Promise<ProcessCard> {
	const processes = readProcessesFromStorage();
	const index = processes.findIndex((item) => item.id === id);
	if (index < 0) {
		throw new Error(`Processo com id ${id} nao encontrado`);
	}

	const updatedProcess: ProcessCard = {
		...processes[index],
		...data,
		id,
	};

	processes[index] = updatedProcess;
	writeProcessesToStorage(processes);
	return updatedProcess;
}

export async function deleteProcess(id: string): Promise<void> {
	const processes = readProcessesFromStorage();
	const filtered = processes.filter((item) => item.id !== id);
	writeProcessesToStorage(filtered);
}
