import axios from "axios";
import { ProcessCard } from "../app/types/kanban";

const api = axios.create({
	baseURL: "http://localhost:3000",
});

export async function getProcesses(): Promise<ProcessCard[]> {
	const response = await api.get("/processes");
	return response.data;
}

export async function getProcessById(id: string): Promise<ProcessCard> {
  const response = await api.get(`/processes/${id}`);
  return response.data;
}

export async function createProcess(data: Omit<ProcessCard, "id">): Promise<ProcessCard> {
  const response = await api.post("/processes", data);
  return response.data;
}

export async function updateProcess(id: string, data: Partial<ProcessCard>): Promise<ProcessCard> {
  const response = await api.put(`/processes/${id}`, data);
  return response.data;
}

export async function deleteProcess(id: string): Promise<void> {
  await api.delete(`/processes/${id}`);
}
