export default function SocialProfileSkeleton() {
  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="mx-auto w-full max-w-[1400px] px-4 py-8">
        <div className="border-b border-white/10 px-6 py-6 lg:px-8">
          <div className="h-10 w-44 rounded-2xl bg-white/8" />
        </div>

        <div className="grid gap-8 px-6 py-8 lg:grid-cols-[240px_minmax(0,1fr)] lg:px-8 2xl:gap-10">
          <div>
            <div className="h-72 rounded-3xl bg-white/8" />
          </div>

          <div className="space-y-4 py-4">
            <div className="h-8 w-32 rounded-2xl bg-white/8" />
            <div className="h-5 w-full rounded-2xl bg-white/8" />
            <div className="h-5 w-11/12 rounded-2xl bg-white/8" />
            <div className="h-5 w-10/12 rounded-2xl bg-white/8" />
            <div className="h-5 w-8/12 rounded-2xl bg-white/8" />
          </div>
        </div>

        <div className="space-y-8 border-t border-white/10 px-6 pt-8 pb-8 lg:px-8">
          <div className="space-y-4">
            <div className="h-8 w-44 rounded-2xl bg-white/8" />
            <div className="h-5 w-[28rem] max-w-full rounded-2xl bg-white/8" />

            <div className="grid gap-6 xl:grid-cols-[minmax(0,1.4fr)_minmax(280px,0.6fr)] xl:items-end">
              <div className="h-80 rounded-[2rem] bg-white/8" />

              <div className="space-y-4 xl:pb-2">
                <div className="h-10 w-11/12 rounded-2xl bg-white/8" />
                <div className="h-5 w-full rounded-2xl bg-white/8" />
                <div className="h-5 w-8/12 rounded-2xl bg-white/8" />
                <div className="h-5 w-48 rounded-2xl bg-white/8" />
                <div className="h-10 w-32 rounded-full bg-white/8" />
              </div>
            </div>
          </div>

          <div className="space-y-4 border-t border-white/10 pt-8">
            <div className="h-8 w-48 rounded-2xl bg-white/8" />
            <div className="h-5 w-72 rounded-2xl bg-white/8" />

            <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
              <div className="h-56 rounded-3xl bg-white/8" />
              <div className="h-56 rounded-3xl bg-white/8" />
              <div className="h-56 rounded-3xl bg-white/8" />
            </div>
          </div>

          <div className="space-y-4 border-t border-white/10 pt-8">
            <div className="h-8 w-40 rounded-2xl bg-white/8" />

            <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
              <div className="h-56 rounded-3xl bg-white/8" />
              <div className="h-56 rounded-3xl bg-white/8" />
              <div className="h-56 rounded-3xl bg-white/8" />
            </div>

            <div className="h-40 rounded-3xl bg-white/8" />
          </div>
        </div>
      </section>
    </main>
  )
}
