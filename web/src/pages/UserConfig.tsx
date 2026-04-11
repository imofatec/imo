import FormSection from '@/components/CreateCourses/FormSection'
import AccountStatusSection from '@/components/UserConfig/AccountStatusSection'
import PasswordSecuritySection from '@/components/UserConfig/PasswordSecuritySection'
import ProfileInfoSection from '@/components/UserConfig/ProfileInfoSection'
import UserConfigSummary from '@/components/UserConfig/UserConfigSummary'
import { useUser } from '@/contexts/UserContext'

export default function UserConfigPage() {
  const { user } = useUser()

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="border-b border-white/10">
        <div className="mx-auto w-full max-w-5xl px-4 py-5">
          <h1 className="text-2xl font-bold text-white">Configurações do usuário</h1>
        </div>
      </section>

      <section className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
        <UserConfigSummary user={user} />

        <FormSection title="Estado da conta">
          <AccountStatusSection user={user} />
        </FormSection>

        <FormSection title="Informações pessoais">
          <ProfileInfoSection user={user} />
        </FormSection>

        <FormSection title="Segurança">
          <PasswordSecuritySection />
        </FormSection>
      </section>
    </main>
  )
}
