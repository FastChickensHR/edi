import { Outlet } from 'react-router-dom'
import styled from 'styled-components'

const Root = styled.div`
  display: flex;
  min-height: 100vh;
`

const BrandPanel = styled.aside`
  display: none;

  @media (min-width: ${({ theme }) => theme.screens.md}) {
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 50%;
    padding: ${({ theme }) => theme.spacing[16]};
    background-color: ${({ theme }) => theme.colors.blue[900]};
    flex-shrink: 0;
  }
`

const ProductName = styled.p`
  font-size: ${({ theme }) => theme.fontSize['3xl']};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  color: ${({ theme }) => theme.colors.white};
  margin: 0 0 ${({ theme }) => theme.spacing[3]};
  letter-spacing: -0.025em;
  line-height: ${({ theme }) => theme.lineHeight.tight};
`

const Tagline = styled.p`
  font-size: ${({ theme }) => theme.fontSize.lg};
  color: ${({ theme }) => theme.colors.blue[200]};
  margin: 0 0 ${({ theme }) => theme.spacing[10]};
  line-height: ${({ theme }) => theme.lineHeight.relaxed};
`

const FeatureList = styled.ul`
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.spacing[4]};
`

const FeatureItem = styled.li`
  display: flex;
  align-items: center;
  gap: ${({ theme }) => theme.spacing[3]};
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.blue[100]};
  line-height: ${({ theme }) => theme.lineHeight.normal};
`

const CheckCircle = styled.span`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 1.25rem;
  height: 1.25rem;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.blue[700]};
  flex-shrink: 0;
`

const FormPanel = styled.main`
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: ${({ theme }) => `${theme.spacing[12]} ${theme.spacing[8]}`};
  background-color: ${({ theme }) => theme.colors.white};
`

const FormContainer = styled.div`
  width: 100%;
  max-width: 24rem;
`

const FEATURES = [
  '834 EDI enrollment, automated',
  'Multi-system integrations',
  'Organization-scoped access control',
]

export function UnauthenticatedLayout() {
  return (
    <Root>
      <BrandPanel>
        <ProductName>FastChickens HR</ProductName>
        <Tagline>Built for HR teams who move fast.</Tagline>
        <FeatureList>
          {FEATURES.map((feature) => (
            <FeatureItem key={feature}>
              <CheckCircle>
                <svg width="10" height="10" viewBox="0 0 10 10" fill="none" aria-hidden="true">
                  <path
                    d="M2 5l2.5 2.5L8 3"
                    stroke="white"
                    strokeWidth="1.5"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                  />
                </svg>
              </CheckCircle>
              {feature}
            </FeatureItem>
          ))}
        </FeatureList>
      </BrandPanel>
      <FormPanel>
        <FormContainer>
          <Outlet />
        </FormContainer>
      </FormPanel>
    </Root>
  )
}
