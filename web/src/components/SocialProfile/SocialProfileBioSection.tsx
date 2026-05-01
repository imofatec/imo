type SocialProfileBioSectionProps = {
  bio: string | null | undefined
}

export default function SocialProfileBioSection({ bio }: SocialProfileBioSectionProps) {
  const normalizedBio = bio?.trim()

  return (
    <section className="rounded-[1.75rem] border border-white/10 bg-[#10052b] p-6">
      <h2 className="text-lg font-semibold text-white">Bio</h2>
      <p className="mt-4 text-sm leading-7 wrap-break-word text-white/78">
        {normalizedBio || 'Bio não adicionada.'}
      </p>
    </section>
  )
}
