import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from './ui/alert-dialog';
import { AlertTriangle } from 'lucide-react';

interface DeleteConfirmDialogProps {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
  processTitle: string;
  processNumber: string;
}

export function DeleteConfirmDialog({
  isOpen,
  onClose,
  onConfirm,
  processTitle,
  processNumber,
}: DeleteConfirmDialogProps) {
  return (
    <AlertDialog open={isOpen} onOpenChange={onClose}>
      <AlertDialogContent>
        <AlertDialogHeader>
          <div className="flex items-center gap-3">
            <div className="bg-red-100 p-2 rounded-full">
              <AlertTriangle className="w-5 h-5 text-red-600" />
            </div>
            <AlertDialogTitle>Excluir Processo?</AlertDialogTitle>
          </div>
          <AlertDialogDescription className="pt-4">
            Você está prestes a excluir o processo:
            <div className="mt-3 p-3 bg-gray-50 rounded-lg border border-gray-200">
              <p className="font-semibold text-gray-900">{processTitle}</p>
              <p className="text-sm text-gray-600 font-mono mt-1">{processNumber}</p>
            </div>
            <p className="mt-4 text-red-600 font-medium">
              Esta ação não pode ser desfeita.
            </p>
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancelar</AlertDialogCancel>
          <AlertDialogAction
            onClick={onConfirm}
            className="bg-red-600 hover:bg-red-700 focus:ring-red-600"
          >
            Sim, Excluir
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
