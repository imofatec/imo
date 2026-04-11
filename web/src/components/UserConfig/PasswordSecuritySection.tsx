import Button from '@/components/ui/Button'
import FormInput from '@/components/ui/FormInput'

export default function PasswordSecuritySection() {
  return (
    <div className="space-y-5">
      <div className="p-4">
        <p className="text-xl text-white/80">Antes de alterar a senha, confirme sua senha atual.</p>
      </div>

      <div className="grid gap-5 md:grid-cols-2">
        <FormInput
          id="current-password"
          type="password"
          label="Senha atual"
          placeholder="Digite sua senha atual"
          className="h-12 rounded-xl bg-white/5"
        />

        <div />

        <FormInput
          id="new-password"
          type="password"
          label="Nova senha"
          placeholder="Digite a nova senha"
          className="h-12 rounded-xl bg-white/5"
        />

        <FormInput
          id="confirm-new-password"
          type="password"
          label="Confirmar nova senha"
          placeholder="Confirme a nova senha"
          className="h-12 rounded-xl bg-white/5"
        />
      </div>

      <div className="flex justify-end">
        <Button
          type="button"
          variant="cyanOutline"
          className="border-cyan/40 text-cyan mt-0! w-auto rounded-full border px-4 py-2 text-sm"
        >
          Atualizar senha
        </Button>
      </div>
    </div>
  )
}
