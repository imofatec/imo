import FormSection from '@/components/CreateCourses/FormSection'
import AccountStatusSection from '@/components/UserConfig/AccountStatusSection'
import PasswordSecuritySection from '@/components/UserConfig/PasswordSecuritySection'
import ProfileInfoSection from '@/components/UserConfig/ProfileInfoSection'
import UserConfigSummary from '@/components/UserConfig/UserConfigSummary'
import { useUserConfigPage } from '@/hooks/useUserConfigPage'

export default function UserConfigPage() {
  const {
    user,
    profileImageSrc,
    hasPendingPhoto,
    isUploadingPhoto,
    isResendingConfirmation,
    photoErrorMessage,
    profileErrorMessage,
    passwordErrorMessage,
    confirmationErrorMessage,
    registerProfile,
    handleProfileSubmit,
    profileErrors,
    isSubmittingProfile,
    registerPassword,
    handlePasswordSubmit,
    passwordErrors,
    isSubmittingPassword,
    handleSelectPhoto,
    handleUploadPhoto,
    handleResendConfirmationEmail,
    onSubmitProfile,
    onSubmitPassword,
  } = useUserConfigPage()

  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="border-b border-white/10">
        <div className="mx-auto w-full max-w-5xl px-4 py-5">
          <h1 className="text-2xl font-bold text-white">Configurações do usuário</h1>
        </div>
      </section>

      <section className="mx-auto w-full max-w-5xl space-y-8 px-4 py-8">
        <UserConfigSummary
          user={user}
          profileImageSrc={profileImageSrc}
          hasPendingPhoto={hasPendingPhoto}
          isUploadingPhoto={isUploadingPhoto}
          photoErrorMessage={photoErrorMessage}
          onSelectPhoto={handleSelectPhoto}
          onUploadPhoto={handleUploadPhoto}
        />

        <FormSection title="Estado da conta">
          <AccountStatusSection
            user={user}
            isResendingConfirmation={isResendingConfirmation}
            errorMessage={confirmationErrorMessage}
            onResendConfirmationEmail={handleResendConfirmationEmail}
          />
        </FormSection>

        <FormSection title="Informações pessoais">
          <ProfileInfoSection
            user={user}
            register={registerProfile}
            errors={profileErrors}
            isSubmitting={isSubmittingProfile}
            errorMessage={profileErrorMessage}
            onSubmit={handleProfileSubmit(onSubmitProfile)}
          />
        </FormSection>

        <FormSection title="Segurança">
          <PasswordSecuritySection
            register={registerPassword}
            errors={passwordErrors}
            isSubmitting={isSubmittingPassword}
            errorMessage={passwordErrorMessage}
            onSubmit={handlePasswordSubmit(onSubmitPassword)}
          />
        </FormSection>
      </section>
    </main>
  )
}
