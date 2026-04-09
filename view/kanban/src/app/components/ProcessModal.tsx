import { useForm } from 'react-hook-form';
import { X } from 'lucide-react';
import { Dialog, DialogContent, DialogHeader, DialogTitle } from './ui/dialog';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Label } from './ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from './ui/select';
import { ProcessCard, Categoria, Prioridade, Instancia } from '../types/kanban';

interface ProcessModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSave: (data: Omit<ProcessCard, 'id'>) => void;
  initialData?: ProcessCard;
  mode: 'create' | 'edit';
}

export type FormData = Omit<ProcessCard, 'id'>;

export function ProcessModal({ isOpen, onClose, onSave, mode }: ProcessModalProps) {
  const { register, handleSubmit, setValue, watch, reset, formState: { errors } } = useForm<FormData>({
    defaultValues: {
      titulo: '',
      numeroProcesso: '',
      cliente: '',
      prazo: '',
      comarca: '',
      categoria: 'Cível',
      prioridade: 'Média',
      instancia: '1º Grau',
      status: 'novos-casos'
    }
  });

  const categoria = watch('categoria');
  const prioridade = watch('prioridade');
  const instancia = watch('instancia');
  const status = watch('status');

  const onSubmit = (data: FormData) => {
    onSave(data);
    reset();
    onClose();
  };

  const handleClose = () => {
    reset();
    onClose();
  };

  return (
    <Dialog open={isOpen} onOpenChange={handleClose}>
      <DialogContent className="max-w-2xl max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle className="text-2xl">
            {mode === 'create' ? 'Novo Processo' : 'Editar Processo'}
          </DialogTitle>
        </DialogHeader>

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6 mt-4">
          {/* Título do Processo */}
          <div className="space-y-2">
            <Label htmlFor="titulo">Ação / Assunto *</Label>
            <Input
              id="titulo"
              {...register('titulo', { required: 'Campo obrigatório' })}
              placeholder="Ex: Ação de Indenização, Divórcio Consensual"
              className={errors.titulo ? 'border-red-500' : ''}
            />
            {errors.titulo && (
              <p className="text-sm text-red-600">{errors.titulo.message}</p>
            )}
          </div>

          {/* Número do Processo */}
          <div className="space-y-2">
            <Label htmlFor="numeroProcesso">Número do Processo *</Label>
            <Input
              id="numeroProcesso"
              {...register('numeroProcesso', { required: 'Campo obrigatório' })}
              placeholder="0000000-00.0000.0.00.0000"
              className={errors.numeroProcesso ? 'border-red-500' : ''}
            />
            {errors.numeroProcesso && (
              <p className="text-sm text-red-600">{errors.numeroProcesso.message}</p>
            )}
          </div>

          {/* Cliente */}
          <div className="space-y-2">
            <Label htmlFor="cliente">Cliente *</Label>
            <Input
              id="cliente"
              {...register('cliente', { required: 'Campo obrigatório' })}
              placeholder="Nome do cliente"
              className={errors.cliente ? 'border-red-500' : ''}
            />
            {errors.cliente && (
              <p className="text-sm text-red-600">{errors.cliente.message}</p>
            )}
          </div>

          {/* Prazo e Vara/Comarca */}
          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="prazo">Próximo Prazo / Data da Audiência *</Label>
              <Input
                id="prazo"
                type="date"
                {...register('prazo', { required: 'Campo obrigatório' })}
                className={errors.prazo ? 'border-red-500' : ''}
              />
              {errors.prazo && (
                <p className="text-sm text-red-600">{errors.prazo.message}</p>
              )}
            </div>

            <div className="space-y-2">
              <Label htmlFor="comarca">Vara / Comarca *</Label>
              <Input
                id="comarca"
                {...register('comarca', { required: 'Campo obrigatório' })}
                placeholder="Ex: 3ª Vara Cível"
                className={errors.comarca ? 'border-red-500' : ''}
              />
              {errors.comarca && (
                <p className="text-sm text-red-600">{errors.comarca.message}</p>
              )}
            </div>
          </div>

          {/* Tags - Área do Direito */}
          <div className="space-y-2">
            <Label>Área do Direito *</Label>
            <Select
              value={categoria}
              onValueChange={(value) => setValue('categoria', value as Categoria)}
            >
              <SelectTrigger>
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="Cível">Cível</SelectItem>
                <SelectItem value="Penal">Penal</SelectItem>
                <SelectItem value="Trabalhista">Trabalhista</SelectItem>
                <SelectItem value="Tributário">Tributário</SelectItem>
                <SelectItem value="Família">Família</SelectItem>
              </SelectContent>
            </Select>
          </div>

          {/* Tags - Urgência */}
          <div className="space-y-2">
            <Label>Urgência / Prioridade *</Label>
            <Select
              value={prioridade}
              onValueChange={(value) => setValue('prioridade', value as Prioridade)}
            >
              <SelectTrigger>
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="Alta">Alta</SelectItem>
                <SelectItem value="Média">Média</SelectItem>
                <SelectItem value="Prazo Hoje">Prazo Hoje</SelectItem>
                <SelectItem value="Liminar">Liminar</SelectItem>
              </SelectContent>
            </Select>
          </div>

          {/* Tags - Instância */}
          <div className="space-y-2">
            <Label>Instância *</Label>
            <Select
              value={instancia}
              onValueChange={(value) => setValue('instancia', value as Instancia)}
            >
              <SelectTrigger>
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="1º Grau">1º Grau</SelectItem>
                <SelectItem value="2º Grau">2º Grau</SelectItem>
                <SelectItem value="STJ/STF">STJ/STF</SelectItem>
              </SelectContent>
            </Select>
          </div>

          {/* Status / Coluna */}
          <div className="space-y-2">
            <Label>Status do Processo *</Label>
            <Select
              value={status}
              onValueChange={(value) => setValue('status', value)}
            >
              <SelectTrigger>
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="novos-casos">Novos Casos / Análise Inicial</SelectItem>
                <SelectItem value="em-andamento">Em Andamento / Prazos Abertos</SelectItem>
                <SelectItem value="aguardando">Aguardando (Juízo / Audiência / Cliente)</SelectItem>
                <SelectItem value="concluidos">Concluídos / Arquivados</SelectItem>
              </SelectContent>
            </Select>
          </div>

          {/* Botões de Ação */}
          <div className="flex justify-end gap-3 pt-4 border-t">
            <Button
              type="button"
              variant="outline"
              onClick={handleClose}
            >
              Cancelar
            </Button>
            <Button type="submit" className="bg-blue-600 hover:bg-blue-700">
              {mode === 'create' ? 'Criar Processo' : 'Salvar Alterações'}
            </Button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
}
